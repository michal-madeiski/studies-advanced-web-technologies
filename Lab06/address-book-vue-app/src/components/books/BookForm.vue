<template>
  <div class="card">
    <h2>{{ book ? 'Edytuj książkę' : 'Dodaj książkę' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Tytuł</label>
        <input v-model="form.title" type="text" placeholder="Tytuł książki" />
      </div>

      <div class="form-group">
        <label>Autor</label>
        <select v-model="form.authorId">
          <option value="" disabled>-- Wybierz autora --</option>
          <option v-for="author in authors" :key="author.id" :value="author.id">
            {{ author.name }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Liczba stron</label>
        <input v-model.number="form.pages" type="number" min="1" placeholder="np. 300" />
      </div>

      <p v-if="error" class="error-message">{{ error }}</p>

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
      error: ''
    }
  },
  methods: {
    handleSubmit() {
      if (!this.form.title || !this.form.authorId || !this.form.pages) {
        this.error = 'Proszę wypełnić wszystkie pola.'
        return
      }
      this.error = ''
      this.$emit('save', {
        title: this.form.title,
        author: { id: this.form.authorId },
        pages: this.form.pages
      })
    }
  }
}
</script>
