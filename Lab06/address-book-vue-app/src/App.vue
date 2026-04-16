<template>
  <component :is="layout">
    <router-view />
  </component>
</template>

<script>
export default {
  name: 'App',
  computed: {
    layout() {
      return this.$route.meta.layout || 'div'
    }
  }
}
</script>

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  color: #2c3e50;
  background: #f5f7fa;
}

button {
  cursor: pointer;
  padding: 6px 14px;
  border-radius: 4px;
  border: none;
  margin: 0;
  font-size: 14px;
  outline: none;
  line-height: 1.4;
  -webkit-appearance: none;
  appearance: none;
  box-shadow: none;
}

button:hover {
  opacity: 0.85;
  border: none;
}

button:focus,
button:active {
  outline: none;
  box-shadow: none;
  border: none;
}

button:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}

button:disabled:hover {
  opacity: 0.35;
}

button.btn-primary {
  background: #2563eb;
  color: #fff;
}

button.btn-danger {
  background: #dc2626;
  color: #fff;
}

button.btn-warning {
  background: #d97706;
  color: #fff;
}

button.btn-secondary {
  background: #6b7280;
  color: #fff;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px 14px;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

th {
  background: #f1f5f9;
  font-weight: 600;
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

tr:hover td {
  background: #f8fafc;
}

input, select {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #d1d5db;
  border-radius: 4px;
  font-size: 14px;
}

input:focus, select:focus {
  outline: none;
  border-color: #2563eb;
}

input.has-error, select.has-error {
  border-color: #dc2626;
  background: #fef2f2;
}

label {
  display: block;
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 4px;
  color: #374151;
}

.form-group {
  margin-bottom: 14px;
}

.error-message {
  color: #dc2626;
  font-size: 13px;
  margin-top: 8px;
}

.success-message {
  color: #16a34a;
  font-size: 13px;
  margin-top: 8px;
}

.pagination {
  display: flex;
  gap: 8px;
  align-items: center;
  margin-top: 16px;
  justify-content: center;
}

.page-info {
  font-size: 14px;
  color: #6b7280;
}

.card {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.08);
  margin-bottom: 24px;
  overflow-x: auto;
}

.actions {
  display: flex;
  gap: 6px;
}
</style>
