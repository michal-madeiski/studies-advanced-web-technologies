<template>
  <div>
    <h1 class="page-title">Witaj w Bibliotece</h1>
    <p class="page-subtitle">System zarządzania biblioteką — wybierz sekcję z menu.</p>

    <div class="tiles">
      <router-link to="/books" class="tile">
        <div class="tile-icon">📚</div>
        <div class="tile-label">Książki</div>
        <div class="tile-desc">Przeglądaj, dodawaj i edytuj książki</div>
      </router-link>

      <router-link to="/authors" class="tile">
        <div class="tile-icon">✍️</div>
        <div class="tile-label">Autorzy</div>
        <div class="tile-desc">Zarządzaj autorami</div>
      </router-link>

      <router-link to="/readers" class="tile">
        <div class="tile-icon">👤</div>
        <div class="tile-label">Czytelnicy</div>
        <div class="tile-desc">Zarządzaj czytelnikami</div>
      </router-link>

      <router-link to="/loans" class="tile">
        <div class="tile-icon">🔖</div>
        <div class="tile-label">Wypożyczenia</div>
        <div class="tile-desc">Śledź wypożyczenia i zwroty</div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.page-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
}

.page-subtitle {
  color: #6b7280;
  margin-bottom: 32px;
  font-size: 15px;
}

.tiles {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
}

.tile {
  background: #fff;
  border-radius: 10px;
  padding: 28px 20px;
  text-decoration: none;
  color: #1e293b;
  box-shadow: 0 1px 4px rgba(0,0,0,0.08);
  transition: box-shadow 0.15s, transform 0.15s;
  display: block;
}

.tile:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.12);
  transform: translateY(-2px);
}

.tile-icon {
  font-size: 36px;
  margin-bottom: 12px;
}

.tile-label {
  font-size: 17px;
  font-weight: 700;
  margin-bottom: 6px;
}

.tile-desc {
  font-size: 13px;
  color: #6b7280;
}
</style>
