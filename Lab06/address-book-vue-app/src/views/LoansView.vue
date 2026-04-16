<template>
  <div>
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px">
      <h1 class="page-title">Wypożyczenia</h1>
      <button class="btn-primary" @click="openAdd">+ Dodaj wypożyczenie</button>
    </div>

    <LoanForm
      v-if="showForm"
      :books="books"
      :readers="readers"
      @save="handleSave"
      @cancel="closeForm"
    />

    <div class="card">
      <LoansTable
        :loans="loans"
        :currentPage="currentPage"
        :hasNextPage="hasNextPage"
        @return="handleReturn"
        @delete="handleDelete"
        @page="changePage"
      />
    </div>
  </div>
</template>

<script>
import LoansTable from '@/components/loans/LoansTable.vue'
import LoanForm from '@/components/loans/LoanForm.vue'

const API = 'http://localhost:8080'

export default {
  name: 'LoansView',
  components: { LoansTable, LoanForm },
  data() {
    return {
      loans: [],
      books: [],
      readers: [],
      showForm: false,
      currentPage: 1,
      pageSize: 5,
      hasNextPage: false
    }
  },
  async mounted() {
    await Promise.all([this.fetchLoans(), this.fetchBooks(), this.fetchReaders()])
  },
  methods: {
    async fetchLoans() {
      try {
        const res = await fetch(`${API}/loans?page=${this.currentPage - 1}&size=${this.pageSize}`)
        const totalCount = Number(res.headers.get('X-Total-Count'))
        this.loans = await res.json()
        this.hasNextPage = this.currentPage < Math.ceil(totalCount / this.pageSize)
        if (this.loans.length === 0 && this.currentPage > 1) {
          this.currentPage = 1
          await this.fetchLoans()
        }
      } catch (e) {
        console.error(e)
      }
    },
    changePage(newPage) {
      this.currentPage = newPage
      this.fetchLoans()
    },
    async fetchBooks() {
      try {
        const res = await fetch(`${API}/books?page=0&size=1000`)
        this.books = await res.json()
      } catch (e) {
        console.error(e)
      }
    },
    async fetchReaders() {
      try {
        const res = await fetch(`${API}/readers?page=0&size=1000`)
        this.readers = await res.json()
      } catch (e) {
        console.error(e)
      }
    },
    openAdd() {
      this.showForm = true
    },
    closeForm() {
      this.showForm = false
    },
    async handleSave(loanData) {
      try {
        await fetch(`${API}/loans`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(loanData)
        })
        this.closeForm()
        await this.fetchLoans()
      } catch (e) {
        console.error(e)
      }
    },
    async handleReturn(id) {
      if (!confirm('Czy potwierdzasz zwrot tej książki?')) return
      try {
        await fetch(`${API}/loans/${id}`, { method: 'PUT' })
        await this.fetchLoans()
      } catch (e) {
        console.error(e)
      }
    },
    async handleDelete(id) {
      if (!confirm('Czy na pewno chcesz usunąć to wypożyczenie?')) return
      try {
        await fetch(`${API}/loans/${id}`, { method: 'DELETE' })
        await this.fetchLoans()
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.page-title { font-size: 24px; font-weight: 700; }
</style>
