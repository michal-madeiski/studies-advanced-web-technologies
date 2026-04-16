<template>
  <div class="card">
    <h2>{{ reader ? 'Edytuj czytelnika' : 'Dodaj czytelnika' }}</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Imię i nazwisko</label>
        <input
          v-model="form.name"
          type="text"
          placeholder="np. Jan Kowalski"
          :class="{ 'has-error': submitting && invalidName }"
          @focus="clearStatus"
          @keypress="clearStatus"
        />
      </div>

      <div class="form-group">
        <label>Email</label>
        <input
          v-model="form.email"
          type="text"
          placeholder="np. jan@mail.com"
          :class="{ 'has-error': submitting && invalidEmail }"
          @focus="clearStatus"
          @keypress="clearStatus"
        />
      </div>

      <p v-if="error && submitting" class="error-message">Proszę wypełnić wskazane pola formularza</p>

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
      submitting: false,
      error: false
    }
  },
  computed: {
    invalidName()  { return !this.form.name },
    invalidEmail() { return !this.form.email }
  },
  watch: {
    reader(newReader) {
      this.form.name  = newReader?.name  || ''
      this.form.email = newReader?.email || ''
      this.submitting = false
      this.error = false
    }
  },
  methods: {
    handleSubmit() {
      this.submitting = true
      if (this.invalidName || this.invalidEmail) {
        this.error = true
        return
      }
      this.error = false
      this.$emit('save', { name: this.form.name, email: this.form.email })
    },
    clearStatus() {
      this.submitting = false
      this.error = false
    }
  }
}
</script>
