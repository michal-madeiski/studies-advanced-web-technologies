<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Imię i nazwisko</th>
          <th>Email</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="readers.length === 0">
          <td colspan="4" style="text-align:center; color:#6b7280">Brak czytelników</td>
        </tr>
        <tr v-for="reader in readers" :key="reader.id">
          <td>{{ reader.id }}</td>
          <td>{{ reader.name }}</td>
          <td>{{ reader.email }}</td>
          <td>
            <div class="actions">
              <button class="btn-warning" @click="$emit('edit', reader)">Edytuj</button>
              <button class="btn-danger" @click="$emit('delete', reader.id)">Usuń</button>
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
  name: 'ReadersTable',
  props: {
    readers: { type: Array, default: () => [] },
    currentPage: { type: Number, default: 1 },
    hasNextPage: { type: Boolean, default: false }
  },
  emits: ['edit', 'delete', 'page']
}
</script>
