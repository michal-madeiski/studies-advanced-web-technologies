<template>
  <div>
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px">
      <h1 class="page-title">Czytelnicy</h1>
      <button class="btn-primary" @click="openAdd">+ Dodaj czytelnika</button>
    </div>

    <ReaderForm
      v-if="showForm"
      :reader="editingReader"
      @save="handleSave"
      @cancel="closeForm"
    />

    <div class="card">
      <ReadersTable
        :readers="readers"
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
import ReadersTable from '@/components/readers/ReadersTable.vue'
import ReaderForm from '@/components/readers/ReaderForm.vue'

const API = 'http://localhost:8080'

export default {
  name: 'ReadersView',
  components: { ReadersTable, ReaderForm },
  data() {
    return {
      readers: [],
      showForm: false,
      editingReader: null,
      currentPage: 1,
      pageSize: 5,
      hasNextPage: false
    }
  },
  async mounted() {
    await this.fetchReaders()
  },
  methods: {
    async fetchReaders() {
      try {
        const res = await fetch(`${API}/readers?page=${this.currentPage - 1}&size=${this.pageSize}`)
        const totalCount = Number(res.headers.get('X-Total-Count'))
        this.readers = await res.json()
        this.hasNextPage = this.currentPage < Math.ceil(totalCount / this.pageSize)
        if (this.readers.length === 0 && this.currentPage > 1) {
          this.currentPage = 1
          await this.fetchReaders()
        }
      } catch (e) {
        console.error(e)
      }
    },
    changePage(newPage) {
      this.currentPage = newPage
      this.fetchReaders()
    },
    openAdd() {
      this.editingReader = null
      this.showForm = true
    },
    openEdit(reader) {
      this.editingReader = reader
      this.showForm = true
    },
    closeForm() {
      this.showForm = false
      this.editingReader = null
    },
    async handleSave(readerData) {
      try {
        if (this.editingReader) {
          await fetch(`${API}/readers/${this.editingReader.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(readerData)
          })
        } else {
          await fetch(`${API}/readers`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(readerData)
          })
        }
        this.closeForm()
        await this.fetchReaders()
      } catch (e) {
        console.error(e)
      }
    },
    async handleDelete(id) {
      if (!confirm('Czy na pewno chcesz usunąć tego czytelnika?')) return
      try {
        const res = await fetch(`${API}/readers/${id}`, { method: 'DELETE' })
        if (!res.ok) {
          alert(await res.text())
          return
        }
        await this.fetchReaders()
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
