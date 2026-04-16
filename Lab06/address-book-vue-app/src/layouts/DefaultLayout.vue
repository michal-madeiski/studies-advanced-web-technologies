<template>
  <div class="layout">
    <nav class="navbar">
      <div class="navbar-brand">Biblioteka</div>
      <div class="navbar-links">
        <router-link to="/">Strona główna</router-link>
        <router-link to="/books">Książki</router-link>
        <router-link to="/authors">Autorzy</router-link>
        <router-link to="/readers">Czytelnicy</router-link>
        <router-link to="/loans">Wypożyczenia</router-link>
      </div>
    </nav>

    <main class="main-content">
      <slot />
    </main>

    <footer class="footer">
      <span>Biblioteka &copy; 2024</span>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'DefaultLayout'
}
</script>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #1e3a5f;
  color: #fff;
  padding: 0 32px;
  height: 56px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.15);
}

.navbar-brand {
  font-size: 20px;
  font-weight: 700;
  letter-spacing: 0.03em;
}

.navbar-links {
  display: flex;
  gap: 8px;
}

.navbar-links a {
  color: #cbd5e1;
  text-decoration: none;
  padding: 6px 14px;
  border-radius: 4px;
  font-size: 14px;
  transition: background 0.15s, color 0.15s;
}

.navbar-links a:hover {
  background: rgba(255,255,255,0.1);
  color: #fff;
}

.navbar-links a.router-link-active {
  background: rgba(255,255,255,0.18);
  color: #fff;
  font-weight: 600;
}

.main-content {
  flex: 1;
  padding: 32px;
  max-width: 1100px;
  width: 100%;
  margin: 0 auto;
}

.footer {
  background: #1e3a5f;
  color: #94a3b8;
  text-align: center;
  padding: 14px;
  font-size: 13px;
}
</style>
