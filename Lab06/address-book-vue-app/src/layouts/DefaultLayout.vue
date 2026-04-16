<template>
  <div class="layout">
    <nav class="navbar">
      <div class="navbar-brand">Biblioteka</div>

      <button class="hamburger" @click="menuOpen = !menuOpen" aria-label="Menu">
        <span :class="{ open: menuOpen }"></span>
        <span :class="{ open: menuOpen }"></span>
        <span :class="{ open: menuOpen }"></span>
      </button>

      <div class="navbar-links" :class="{ 'menu-open': menuOpen }">
        <router-link to="/" @click="menuOpen = false">Strona główna</router-link>
        <router-link to="/books" @click="menuOpen = false">Książki</router-link>
        <router-link to="/authors" @click="menuOpen = false">Autorzy</router-link>
        <router-link to="/readers" @click="menuOpen = false">Czytelnicy</router-link>
        <router-link to="/loans" @click="menuOpen = false">Wypożyczenia</router-link>
      </div>
    </nav>

    <main class="main-content">
      <slot />
    </main>

    <footer class="footer">
      <span>Biblioteka &copy; Mateusz Janiszewski, Michał Madeiski 2026</span>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'DefaultLayout',
  data() {
    return {
      menuOpen: false
    }
  }
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
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
  position: relative;
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
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.navbar-links a.router-link-active {
  background: rgba(255, 255, 255, 0.18);
  color: #fff;
  font-weight: 600;
}

/* Hamburger button */
.hamburger {
  display: none;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 6px;
  width: 36px;
  height: 36px;
}

.hamburger span {
  display: block;
  width: 22px;
  height: 2px;
  background: #cbd5e1;
  border-radius: 2px;
  transition: transform 0.25s, opacity 0.25s;
  transform-origin: center;
}

/* Animacja X */
.hamburger span:nth-child(1).open {
  transform: translateY(7px) rotate(45deg);
}

.hamburger span:nth-child(2).open {
  opacity: 0;
}

.hamburger span:nth-child(3).open {
  transform: translateY(-7px) rotate(-45deg);
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

/* Mobile */
@media (max-width: 768px) {
  .navbar {
    flex-wrap: wrap;
    height: auto;
    padding: 12px 20px;
  }

  .hamburger {
    display: flex;
  }

  .navbar-links {
    display: none;
    flex-direction: column;
    width: 100%;
    padding: 8px 0 4px;
    gap: 2px;
  }

  .navbar-links.menu-open {
    display: flex;
  }

  .navbar-links a {
    padding: 10px 12px;
    font-size: 15px;
  }

  .main-content {
    padding: 20px 16px;
  }
}
</style>
