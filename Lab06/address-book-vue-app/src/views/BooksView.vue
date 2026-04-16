<template>
  <div>
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px">
      <h1 class="page-title">Książki</h1>
      <button class="btn-primary" @click="openAdd">+ Dodaj książkę</button>
    </div>

    <BookForm
      v-if="showForm"
      :book="editingBook"
      :authors="authors"
      @save="handleSave"
      @cancel="closeForm"
    />

    <div class="card">
      <BooksTable
        :books="books"
        :currentPage="currentPage"
        :hasNextPage="hasNextPage"
        @edit="openEdit"
        @delete="handleDelete"
        @page="changePage"
      />
    </div>
  </div>
</template>

<script>
import BooksTable from '@/components/books/BooksTable.vue'
import BookForm from '@/components/books/BookForm.vue'

const API = 'http://localhost:8080'

export default {
  name: 'BooksView',
  components: { BooksTable, BookForm },
  data() {
    return {
      books: [],
      authors: [],
      showForm: false,
      editingBook: null,
      currentPage: 1,
      pageSize: 5,
      hasNextPage: false
    }
  },
  async mounted() {
    await Promise.all([this.fetchBooks(), this.fetchAuthors()])
  },
  methods: {
    async fetchBooks() {
      try {
        const res = await fetch(`${API}/books?page=${this.currentPage - 1}&size=${this.pageSize}`)
        const totalCount = Number(res.headers.get('X-Total-Count'))
        this.books = await res.json()
        this.hasNextPage = this.currentPage < Math.ceil(totalCount / this.pageSize)
        if (this.books.length === 0 && this.currentPage > 1) {
          this.currentPage = 1
          await this.fetchBooks()
        }
      } catch (e) {
        console.error(e)
      }
    },
    changePage(newPage) {
      this.currentPage = newPage
      this.fetchBooks()
    },
    async fetchAuthors() {
      try {
        const res = await fetch(`${API}/authors?page=0&size=1000`)
        this.authors = await res.json()
      } catch (e) {
        console.error(e)
      }
    },
    openAdd() {
      this.editingBook = null
      this.showForm = true
    },
    openEdit(book) {
      this.editingBook = book
      this.showForm = true
    },
    closeForm() {
      this.showForm = false
      this.editingBook = null
    },
    async handleSave(bookData) {
      try {
        if (this.editingBook) {
          await fetch(`${API}/books/${this.editingBook.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(bookData)
          })
        } else {
          await fetch(`${API}/books`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(bookData)
          })
        }
        this.closeForm()
        await this.fetchBooks()
      } catch (e) {
        console.error(e)
      }
    },
    async handleDelete(id) {
      if (!confirm('Czy na pewno chcesz usunąć tę książkę?')) return
      try {
        const res = await fetch(`${API}/books/${id}`, { method: 'DELETE' })
        if (!res.ok) {
          alert(await res.text())
          return
        }
        await this.fetchBooks()
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.page-title {
  font-size: 24px;
  font-weight: 700;
}
</style>
