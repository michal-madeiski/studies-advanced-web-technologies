<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Imię i nazwisko</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="authors.length === 0">
          <td colspan="3" style="text-align:center; color:#6b7280">Brak autorów</td>
        </tr>
        <tr v-for="author in authors" :key="author.id">
          <td>{{ author.id }}</td>
          <td>{{ author.name }}</td>
          <td>
            <div class="actions">
              <button class="btn-warning" @click="$emit('edit', author)">Edytuj</button>
              <button class="btn-danger" @click="$emit('delete', author.id)">Usuń</button>
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
  name: 'AuthorsTable',
  props: {
    authors: { type: Array, default: () => [] },
    currentPage: { type: Number, default: 1 },
    hasNextPage: { type: Boolean, default: false }
  },
  emits: ['edit', 'delete', 'page']
}
</script>
