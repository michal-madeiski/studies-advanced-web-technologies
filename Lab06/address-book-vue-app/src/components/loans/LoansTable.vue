<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Książka</th>
          <th>Czytelnik</th>
          <th>Data wypożyczenia</th>
          <th>Data zwrotu</th>
          <th>Status</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="loans.length === 0">
          <td colspan="7" style="text-align:center; color:#6b7280">Brak wypożyczeń</td>
        </tr>
        <tr v-for="loan in loans" :key="loan.id">
          <td>{{ loan.id }}</td>
          <td>{{ loan.book?.title }}</td>
          <td>{{ loan.reader?.name }}</td>
          <td>{{ loan.loanDate }}</td>
          <td>{{ loan.returnDate || '—' }}</td>
          <td>
            <span :class="loan.returnDate ? 'badge-returned' : 'badge-active'">
              {{ loan.returnDate ? 'Zwrócona' : 'Aktywne' }}
            </span>
          </td>
          <td>
            <div class="actions">
              <button
                v-if="!loan.returnDate"
                class="btn-primary"
                @click="$emit('return', loan.id)"
              >Zwróć</button>
              <button class="btn-danger" @click="$emit('delete', loan.id)">Usuń</button>
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
  name: 'LoansTable',
  props: {
    loans: { type: Array, default: () => [] },
    currentPage: { type: Number, default: 1 },
    hasNextPage: { type: Boolean, default: false }
  },
  emits: ['return', 'delete', 'page']
}
</script>

<style scoped>
.badge-active {
  background: #fef3c7;
  color: #92400e;
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
}

.badge-returned {
  background: #d1fae5;
  color: #065f46;
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
}
</style>
