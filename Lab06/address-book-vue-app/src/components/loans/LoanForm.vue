<template>
  <div class="card">
    <h2>Dodaj wypożyczenie</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Książka</label>
        <select
          v-model="form.bookId"
          :class="{ 'has-error': submitting && invalidBook }"
          @focus="clearStatus"
        >
          <option value="" disabled>-- Wybierz książkę --</option>
          <option v-for="book in books" :key="book.id" :value="book.id">
            {{ book.title }} ({{ book.author?.name }})
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Czytelnik</label>
        <select
          v-model="form.readerId"
          :class="{ 'has-error': submitting && invalidReader }"
          @focus="clearStatus"
        >
          <option value="" disabled>-- Wybierz czytelnika --</option>
          <option v-for="reader in readers" :key="reader.id" :value="reader.id">
            {{ reader.name }}
          </option>
        </select>
      </div>

      <p v-if="error && submitting" class="error-message">Proszę wypełnić wskazane pola formularza</p>

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
      submitting: false,
      error: false
    }
  },
  computed: {
    invalidBook()   { return !this.form.bookId },
    invalidReader() { return !this.form.readerId }
  },
  methods: {
    handleSubmit() {
      this.submitting = true
      if (this.invalidBook || this.invalidReader) {
        this.error = true
        return
      }
      this.error = false
      this.$emit('save', {
        book: { id: this.form.bookId },
        reader: { id: this.form.readerId }
      })
    },
    clearStatus() {
      this.submitting = false
      this.error = false
    }
  }
}
</script>
