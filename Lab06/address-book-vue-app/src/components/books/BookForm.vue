<template>
  <div class="card">
    <h2>{{ book ? 'Edytuj książkę' : 'Dodaj książkę' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Tytuł</label>
        <input
          v-model="form.title"
          type="text"
          placeholder="Tytuł książki"
          :class="{ 'has-error': submitting && invalidTitle }"
          @focus="clearStatus"
          @keypress="clearStatus"
        />
      </div>

      <div class="form-group">
        <label>Autor</label>
        <select
          v-model="form.authorId"
          :class="{ 'has-error': submitting && invalidAuthor }"
          @focus="clearStatus"
        >
          <option value="" disabled>-- Wybierz autora --</option>
          <option v-for="author in authors" :key="author.id" :value="author.id">
            {{ author.name }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Liczba stron</label>
        <input
          v-model.number="form.pages"
          type="number"
          min="1"
          placeholder="np. 300"
          :class="{ 'has-error': submitting && invalidPages }"
          @focus="clearStatus"
          @keypress="clearStatus"
        />
      </div>

      <p v-if="error && submitting" class="error-message">Proszę wypełnić wskazane pola formularza</p>

      <div class="actions" style="margin-top: 16px">
        <button type="submit" class="btn-primary">{{ book ? 'Zapisz' : 'Dodaj' }}</button>
        <button type="button" class="btn-secondary" @click="$emit('cancel')">Anuluj</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'BookForm',
  props: {
    book: { type: Object, default: null },
    authors: { type: Array, default: () => [] }
  },
  emits: ['save', 'cancel'],
  data() {
    return {
      form: {
        title: this.book?.title || '',
        authorId: this.book?.author?.id || '',
        pages: this.book?.pages || ''
      },
      submitting: false,
      error: false
    }
  },
  computed: {
    invalidTitle()  { return !this.form.title },
    invalidAuthor() { return !this.form.authorId },
    invalidPages()  { return !this.form.pages }
  },
  watch: {
    book(newBook) {
      this.form.title    = newBook?.title      || ''
      this.form.authorId = newBook?.author?.id || ''
      this.form.pages    = newBook?.pages      || ''
      this.submitting = false
      this.error = false
    }
  },
  methods: {
    handleSubmit() {
      this.submitting = true
      if (this.invalidTitle || this.invalidAuthor || this.invalidPages) {
        this.error = true
        return
      }
      this.error = false
      this.$emit('save', {
        title: this.form.title,
        author: { id: this.form.authorId },
        pages: this.form.pages
      })
    },
    clearStatus() {
      this.submitting = false
      this.error = false
    }
  }
}
</script>
