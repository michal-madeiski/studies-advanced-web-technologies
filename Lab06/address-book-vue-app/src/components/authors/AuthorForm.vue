<template>
  <div class="card">
    <h2>{{ author ? 'Edytuj autora' : 'Dodaj autora' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Imię i nazwisko</label>
        <input
          v-model="form.name"
          type="text"
          placeholder="np. Adam Mickiewicz"
          :class="{ 'has-error': submitting && invalidName }"
          @focus="clearStatus"
          @keypress="clearStatus"
        />
      </div>

      <p v-if="error && submitting" class="error-message">Proszę wypełnić wskazane pola formularza</p>

      <div class="actions" style="margin-top: 16px">
        <button type="submit" class="btn-primary">{{ author ? 'Zapisz' : 'Dodaj' }}</button>
        <button type="button" class="btn-secondary" @click="$emit('cancel')">Anuluj</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AuthorForm',
  props: {
    author: { type: Object, default: null }
  },
  emits: ['save', 'cancel'],
  data() {
    return {
      form: { name: this.author?.name || '' },
      submitting: false,
      error: false
    }
  },
  computed: {
    invalidName() { return !this.form.name }
  },
  watch: {
    author(newAuthor) {
      this.form.name = newAuthor?.name || ''
      this.submitting = false
      this.error = false
    }
  },
  methods: {
    handleSubmit() {
      this.submitting = true
      if (this.invalidName) {
        this.error = true
        return
      }
      this.error = false
      this.$emit('save', { name: this.form.name })
    },
    clearStatus() {
      this.submitting = false
      this.error = false
    }
  }
}
</script>
