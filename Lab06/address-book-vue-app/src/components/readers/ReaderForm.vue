<template>
  <div class="card">
    <h2>{{ reader ? 'Edytuj czytelnika' : 'Dodaj czytelnika' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Imię i nazwisko</label>
        <input v-model="form.name" type="text" placeholder="np. Jan Kowalski" />
      </div>

      <div class="form-group">
        <label>Email</label>
        <input v-model="form.email" type="email" placeholder="np. jan@mail.com" />
      </div>

      <p v-if="error" class="error-message">{{ error }}</p>

      <div class="actions" style="margin-top: 16px">
        <button type="submit" class="btn-primary">{{ reader ? 'Zapisz' : 'Dodaj' }}</button>
        <button type="button" class="btn-secondary" @click="$emit('cancel')">Anuluj</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'ReaderForm',
  props: {
    reader: { type: Object, default: null }
  },
  emits: ['save', 'cancel'],
  data() {
    return {
      form: {
        name: this.reader?.name || '',
        email: this.reader?.email || ''
      },
      error: ''
    }
  },
  methods: {
    handleSubmit() {
      if (!this.form.name || !this.form.email) {
        this.error = 'Proszę wypełnić wszystkie pola.'
        return
      }
      this.error = ''
      this.$emit('save', { name: this.form.name, email: this.form.email })
    }
  }
}
</script>
