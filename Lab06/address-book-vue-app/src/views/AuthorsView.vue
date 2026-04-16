<template>
  <div>
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px">
      <h1 class="page-title">Autorzy</h1>
      <button class="btn-primary" @click="openAdd">+ Dodaj autora</button>
    </div>

    <AuthorForm
      v-if="showForm"
      :author="editingAuthor"
      @save="handleSave"
      @cancel="closeForm"
    />

    <div class="card">
      <AuthorsTable
        :authors="authors"
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
import AuthorsTable from '@/components/authors/AuthorsTable.vue'
import AuthorForm from '@/components/authors/AuthorForm.vue'

const API = 'http://localhost:8080'

export default {
  name: 'AuthorsView',
  components: { AuthorsTable, AuthorForm },
  data() {
    return {
      authors: [],
      showForm: false,
      editingAuthor: null,
      currentPage: 1,
      pageSize: 5,
      hasNextPage: false
    }
  },
  async mounted() {
    await this.fetchAuthors()
  },
  methods: {
    async fetchAuthors() {
      try {
        const res = await fetch(`${API}/authors?page=${this.currentPage - 1}&size=${this.pageSize}`)
        const totalCount = Number(res.headers.get('X-Total-Count'))
        this.authors = await res.json()
        this.hasNextPage = this.currentPage < Math.ceil(totalCount / this.pageSize)
        if (this.authors.length === 0 && this.currentPage > 1) {
          this.currentPage = 1
          await this.fetchAuthors()
        }
      } catch (e) {
        console.error(e)
      }
    },
    changePage(newPage) {
      this.currentPage = newPage
      this.fetchAuthors()
    },
    openAdd() {
      this.editingAuthor = null
      this.showForm = true
    },
    openEdit(author) {
      this.editingAuthor = author
      this.showForm = true
    },
    closeForm() {
      this.showForm = false
      this.editingAuthor = null
    },
    async handleSave(authorData) {
      try {
        if (this.editingAuthor) {
          await fetch(`${API}/authors/${this.editingAuthor.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(authorData)
          })
        } else {
          await fetch(`${API}/authors`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(authorData)
          })
        }
        this.closeForm()
        await this.fetchAuthors()
      } catch (e) {
        console.error(e)
      }
    },
    async handleDelete(id) {
      if (!confirm('Czy na pewno chcesz usunąć tego autora?')) return
      try {
        const res = await fetch(`${API}/authors/${id}`, { method: 'DELETE' })
        if (!res.ok) {
          alert(await res.text())
          return
        }
        await this.fetchAuthors()
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
