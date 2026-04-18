// const { GraphQLServer } = require('graphql-yoga');
// const axios = require("axios");

// // const usersList = [
// //     { id: 1, name: "Jan Konieczny", email: "jan.konieczny@wonet.pl", login: "jkonieczny" },
// //     { id: 2, name: "Anna Wesołowska", email: "anna.w@sad.gov.pl", login: "anna.wesolowska" },
// //     { id: 3, name: "Piotr Waleczny", email: "piotr.waleczny@gp.pl", login: "p.waleczny" }
// // ];

// // const todosList = [
// //     { id: 1, title: "Naprawić samochód", completed: false, user_id: 3},
// //     { id: 2, title: "Posprzątać garaż", completed: true, user_id: 3 },
// //     { id: 3, title: "Napisać e-mail", completed: false, user_id: 3},
// //     { id: 4, title: "Odebrać buty", completed: false, user_id: 2},
// //     { id: 5, title: "Wysłać paczkę", completed: true, user_id: 2},
// //     { id: 6, title: "Zamówic kuriera", completed: false, user_id: 3 }
// // ];

// // function todoById(parent, args, context, info) {
// //     return todosList.find(t => t.id == args.id);
// // }

// // function userById(parent, args, context, info) {
// //     return usersList.find(u => u.id == args.id);
// // }

// async function getRestUsersList(){
//     try {
//         const users = await axios.get("https://jsonplaceholder.typicode.com/users");
//         return users.data.map(({ id, name, email, username }) => ({
//             id: id,
//             name: name,
//             email: email,
//             login: username,
//         }));
//     } catch (error) { throw error; }
// }

// async function getRestTodosList(){
//     try {
//         const response = await axios.get("https://jsonplaceholder.typicode.com/todos");
//         return response.data;
//     } catch (error) { throw error; }
// }

// async function userById(id) {
//     try {
//         const response = await axios.get(`https://jsonplaceholder.typicode.com/users/${id}`);
//         const u = response.data;
//         return {
//             id: u.id,
//             name: u.name,
//             email: u.email,
//             login: u.username
//         };
//     } catch (error) { return null; }
// }

// async function todoById(id) {
//     try {
//         const response = await axios.get(`https://jsonplaceholder.typicode.com/todos/${id}`);
//         return response.data;
//     } catch (error) { return null; }
// }

// const resolvers = {
//     Query: {
//         // users: () => usersList,
//         // todos: () => todosList,
//         // todo: (parent, args, context, info) => todoById(parent, args, context, info),
//         // user: (parent, args, context, info) => userById(parent, args, context, info),

//         users: async () => await getRestUsersList(),
//         todos: async () => await getRestTodosList(),
//         todo: async (parent, args) => await todoById(args.id),
//         user: async (parent, args) => await userById(args.id),
//     },
//     User: {
//         // todos: (parent, args, context, info) => {
//         //     return todosList.filter(t => t.user_id == parent.id);
//         // }

//         todos: async (parent) => {
//             try {
//                 const response = await axios.get(`https://jsonplaceholder.typicode.com/users/${parent.id}/todos`);
//                 return response.data;
//             } catch (error) { return []; }
//         }
//     },
//     ToDoItem: {
//         // user: (parent, args, context, info) => {
//         //     return usersList.find(u => u.id == parent.user_id);
//         // }

//         user: async (parent) => {
//             return await userById(parent.userId);
//         }
//     }
// };

// const server = new GraphQLServer({
//     typeDefs: './src/schema.graphql',
//     resolvers,
// });

// server.start(() => console.log('Server is running on http://localhost:4000'));


// ==================================================================================================================================
// DATABASE VERSION
const { GraphQLServer } = require('graphql-yoga');
const sqlite3 = require('sqlite3');
const { open } = require('sqlite');

let db;

const resolvers = {
    Query: {
        users: async () => await db.all("SELECT * FROM users"),
        todos: async () => {
            const todos = await db.all("SELECT * FROM todos");
            return todos.map(t => ({ ...t, completed: Boolean(t.completed) }));
        },
        user: async (_, { id }) => await db.get("SELECT * FROM users WHERE id = ?", [id]),
        todo: async (_, { id }) => {
            const t = await db.get("SELECT * FROM todos WHERE id = ?", [id]);
            if (t) t.completed = Boolean(t.completed);
            return t;
        }
    },

    Mutation: {
        addUser: async (_, { name, email, login }) => {
            const result = await db.run("INSERT INTO users (name, email, login) VALUES (?, ?, ?)", [name, email, login]);
            return await db.get("SELECT * FROM users WHERE id = ?", [result.lastID]);
        },
        updateUser: async (_, { id, name, email, login }) => {
            const user = await db.get("SELECT * FROM users WHERE id = ?", [id]);
            if (!user) throw new Error("Użytkownik nie istnieje");
            
            const newName = name !== undefined ? name : user.name;
            const newEmail = email !== undefined ? email : user.email;
            const newLogin = login !== undefined ? login : user.login;
            
            await db.run("UPDATE users SET name = ?, email = ?, login = ? WHERE id = ?", [newName, newEmail, newLogin, id]);
            return await db.get("SELECT * FROM users WHERE id = ?", [id]);
        },
        deleteUser: async (_, { id }) => {
            await db.run("DELETE FROM users WHERE id = ?", [id]);
            await db.run("DELETE FROM todos WHERE user_id = ?", [id]);
            return id;
        },

        addTodo: async (_, { title, completed, user_id }) => {
            const isCompleted = completed ? 1 : 0;
            const result = await db.run("INSERT INTO todos (title, completed, user_id) VALUES (?, ?, ?)", [title, isCompleted, user_id]);
            
            const t = await db.get("SELECT * FROM todos WHERE id = ?", [result.lastID]);
            t.completed = Boolean(t.completed);
            return t;
        },
        updateTodo: async (_, { id, title, completed }) => {
            const t = await db.get("SELECT * FROM todos WHERE id = ?", [id]);
            if (!t) throw new Error("Zadanie nie istnieje");
            
            const newTitle = title !== undefined ? title : t.title;
            const newCompleted = completed !== undefined ? (completed ? 1 : 0) : t.completed;
            
            await db.run("UPDATE todos SET title = ?, completed = ? WHERE id = ?", [newTitle, newCompleted, id]);
            const updated = await db.get("SELECT * FROM todos WHERE id = ?", [id]);
            updated.completed = Boolean(updated.completed);
            return updated;
        },
        deleteTodo: async (_, { id }) => {
            await db.run("DELETE FROM todos WHERE id = ?", [id]);
            return id;
        }
    },
    
    User: {
        todos: async (parent) => {
            const todos = await db.all("SELECT * FROM todos WHERE user_id = ?", [parent.id]);
            return todos.map(t => ({ ...t, completed: Boolean(t.completed) }));
        }
    },
    ToDoItem: {
        user: async (parent) => {
            return await db.get("SELECT * FROM users WHERE id = ?", [parent.user_id]);
        }
    }
};


const server = new GraphQLServer({
    typeDefs: './src/schema.graphql',
    resolvers,
});

async function startApp() {
    db = await open({
        filename: './database.sqlite',
        driver: sqlite3.Database
    });

    await db.exec(`
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            email TEXT NOT NULL,
            login TEXT NOT NULL
        )
    `);

    await db.exec(`
        CREATE TABLE IF NOT EXISTS todos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            completed INTEGER NOT NULL,
            user_id INTEGER NOT NULL,
            FOREIGN KEY (user_id) REFERENCES users(id)
        )
    `);

    const count = await db.get("SELECT COUNT(*) as count FROM users");
    
    if (count.count === 0) {
        await db.run("INSERT INTO users (name, email, login) VALUES ('Jan Konieczny', 'jan.konieczny@wonet.pl', 'jkonieczny')");
        await db.run("INSERT INTO users (name, email, login) VALUES ('Anna Wesołowska', 'anna.w@sad.gov.pl', 'anna.wesolowska')");
        await db.run("INSERT INTO users (name, email, login) VALUES ('Piotr Waleczny', 'piotr.waleczny@gp.pl', 'p.waleczny')");

        await db.run("INSERT INTO todos (title, completed, user_id) VALUES ('Naprawić samochód', 0, 3)");
        await db.run("INSERT INTO todos (title, completed, user_id) VALUES ('Posprzątać garaż', 1, 3)");
        await db.run("INSERT INTO todos (title, completed, user_id) VALUES ('Napisać e-mail', 0, 3)");
        await db.run("INSERT INTO todos (title, completed, user_id) VALUES ('Odebrać buty', 0, 2)");
        await db.run("INSERT INTO todos (title, completed, user_id) VALUES ('Wysłać paczkę', 1, 2)");
        await db.run("INSERT INTO todos (title, completed, user_id) VALUES ('Zamówic kuriera', 0, 3)");
    }

    server.start(() => console.log('Server is running on http://localhost:4000'));
}

startApp();