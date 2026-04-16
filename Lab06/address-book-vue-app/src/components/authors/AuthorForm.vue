<template>
  <div class="card">
    <h2>{{ author ? 'Edytuj autora' : 'Dodaj autora' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Imię i nazwisko</label>
        <input v-model="form.name" type="text" placeholder="np. Adam Mickiewicz" />
      </div>

      <p v-if="error" class="error-message">{{ error }}</p>

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
      error: ''
    }
  },
  methods: {
    handleSubmit() {
      if (!this.form.name) {
        this.error = 'Proszę podać imię i nazwisko autora.'
        return
      }
      this.error = ''
      this.$emit('save', { name: this.form.name })
    }
  }
}
</script>
