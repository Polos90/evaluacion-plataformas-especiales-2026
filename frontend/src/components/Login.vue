<template>
  <div class="container">
    <div class="card login-card">
      <h1>Iniciar sesión</h1>

      <form @submit.prevent="submit">
        <label>Usuario</label>
        <input v-model="username" required />

        <label>Contraseña</label>
        <input v-model="password" type="password" required />

        <button :disabled="loading">
          {{ loading ? 'Validando...' : 'Ingresar' }}
        </button>

        <p v-if="error" class="error">{{ error }}</p>
      </form>

      <small>Usuario de prueba: admin / Admin123</small>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../services/api'

const router = useRouter()
const username = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function submit() {
  error.value = ''
  loading.value = true

  try {
    await login(username.value, password.value)
    sessionStorage.setItem('authenticated', 'true')
    router.push('/operacion')
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>
