<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Tytuł</th>
          <th>Autor</th>
          <th>Strony</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="books.length === 0">
          <td colspan="5" style="text-align:center; color:#6b7280">Brak książek</td>
        </tr>
        <tr v-for="book in books" :key="book.id">
          <td>{{ book.id }}</td>
          <td>{{ book.title }}</td>
          <td>{{ book.author?.name }}</td>
          <td>{{ book.pages }}</td>
          <td>
            <div class="actions">
              <button class="btn-warning" @click="$emit('edit', book)">Edytuj</button>
              <button class="btn-danger" @click="$emit('delete', book.id)">Usuń</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="pagination" v-if="currentPage > 1 || hasNextPage">
      <button class="btn-secondary" :disabled="currentPage === 1" @click="$emit('page', currentPage - 1)">
        &laquo; Poprzednia
      </button>
      <span class="page-info">Strona {{ currentPage }}</span>
      <button class="btn-secondary" :disabled="!hasNextPage" @click="$emit('page', currentPage + 1)">
        Następna &raquo;
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BooksTable',
  props: {
    books: { type: Array, default: () => [] },
    currentPage: { type: Number, default: 1 },
    hasNextPage: { type: Boolean, default: false }
  },
  emits: ['edit', 'delete', 'page']
}
</script>
