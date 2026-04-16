<template>
  <div class="card">
    <h2>Dodaj wypożyczenie</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Książka</label>
        <select v-model="form.bookId">
          <option value="" disabled>-- Wybierz książkę --</option>
          <option v-for="book in books" :key="book.id" :value="book.id">
            {{ book.title }} ({{ book.author?.name }})
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Czytelnik</label>
        <select v-model="form.readerId">
          <option value="" disabled>-- Wybierz czytelnika --</option>
          <option v-for="reader in readers" :key="reader.id" :value="reader.id">
            {{ reader.name }}
          </option>
        </select>
      </div>

      <p v-if="error" class="error-message">{{ error }}</p>

      <div class="actions" style="margin-top: 16px">
        <button type="submit" class="btn-primary">Dodaj</button>
        <button type="button" class="btn-secondary" @click="$emit('cancel')">Anuluj</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoanForm',
  props: {
    books: { type: Array, default: () => [] },
    readers: { type: Array, default: () => [] }
  },
  emits: ['save', 'cancel'],
  data() {
    return {
      form: { bookId: '', readerId: '' },
      error: ''
    }
  },
  methods: {
    handleSubmit() {
      if (!this.form.bookId || !this.form.readerId) {
        this.error = 'Proszę wybrać książkę i czytelnika.'
        return
      }
      this.error = ''
      this.$emit('save', {
        book: { id: this.form.bookId },
        reader: { id: this.form.readerId }
      })
    }
  }
}
</script>
