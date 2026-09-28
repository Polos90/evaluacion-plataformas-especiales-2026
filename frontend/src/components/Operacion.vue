<template>
  <div class="container">
    <div class="card">
      <div class="header">
        <h1>Operación</h1>
        <button class="secondary" @click="logout">Salir</button>
      </div>

      <form @submit.prevent="submit">
        <label>Operación</label>
        <input v-model="form.operacion" placeholder="venta" required />

        <label>Importe</label>
        <input v-model="form.importe" type="number" step="0.01" min="0.01" required />

        <label>Cliente</label>
        <input v-model="form.cliente" placeholder="Angel" required />

        <label>Secreto</label>
        <input v-model="form.secreto" type="password" placeholder="Cualquier palabra" required />

        <button :disabled="loading">
          {{ loading ? 'Procesando...' : 'Enviar operación' }}
        </button>
      </form>

      <div v-if="success" class="success">
        <strong>Operación procesada</strong>
        <pre>{{ JSON.stringify(success, null, 2) }}</pre>
      </div>

      <p v-if="error" class="error">{{ error }}</p>
    </div>

    <div class="card">
      <div class="header">
        <h2>Transacciones</h2>
        <button class="secondary" @click="loadTransactions">Actualizar</button>
      </div>

      <div class="filters">
        <input v-model.number="page" type="number" min="0" placeholder="Página" />
        <input v-model.number="size" type="number" min="1" placeholder="Registros" />
        <select v-model="sortBy">
          <option value="id">ID</option>
          <option value="operacion">Operación</option>
          <option value="importe">Importe</option>
          <option value="cliente">Cliente</option>
          <option value="estatus">Estatus</option>
        </select>
        <select v-model="direction">
          <option value="asc">ASC</option>
          <option value="desc">DESC</option>
        </select>
        <button @click="loadTransactions">Consultar</button>
      </div>

      <table v-if="transactions.length">
        <thead>
          <tr>
            <th>ID</th>
            <th>Operación</th>
            <th>Importe</th>
            <th>Cliente</th>
            <th>Referencia</th>
            <th>Estatus</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="t in transactions" :key="t.id">
            <td>{{ t.id }}</td>
            <td>{{ t.operacion }}</td>
            <td>{{ t.importe }}</td>
            <td>{{ t.cliente }}</td>
            <td>{{ t.referencia }}</td>
            <td>{{ t.estatus }}</td>
            <td>
              <button
                v-if="t.estatus === 'Aprobada'"
                class="danger"
                @click="cancelar(t.id)"
              >
                Cancelar
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <p v-else>No hay transacciones.</p>

      <div class="pagination">
        <button :disabled="page === 0" @click="previous">Anterior</button>
        <span>Página {{ page + 1 }} de {{ totalPages || 1 }}</span>
        <button :disabled="page + 1 >= totalPages" @click="next">Siguiente</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  cancelarTransaccion,
  crearOperacion,
  listarTransacciones
} from '../services/api'
import { encryptAES256GCM } from '../services/crypto'

const router = useRouter()

const form = ref({
  operacion: 'venta',
  importe: '',
  cliente: '',
  secreto: ''
})

const loading = ref(false)
const success = ref(null)
const error = ref('')

const transactions = ref([])
const page = ref(0)
const size = ref(10)
const totalPages = ref(0)
const sortBy = ref('id')
const direction = ref('asc')

async function submit() {
  error.value = ''
  success.value = null
  loading.value = true

  try {
    const secretoCifrado = await encryptAES256GCM(form.value.secreto)

    success.value = await crearOperacion({
      operacion: form.value.operacion,
      importe: Number(form.value.importe),
      cliente: form.value.cliente,
      secreto: secretoCifrado
    })

    form.value.importe = ''
    form.value.cliente = ''
    form.value.secreto = ''

    await loadTransactions()
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}

async function loadTransactions() {
  try {
    const data = await listarTransacciones({
      page: page.value,
      size: size.value,
      sortBy: sortBy.value,
      direction: direction.value
    })

    transactions.value = data.content
    totalPages.value = data.totalPages
  } catch (e) {
    error.value = e.message
  }
}

async function cancelar(id) {
  try {
    await cancelarTransaccion(id)
    await loadTransactions()
  } catch (e) {
    error.value = e.message
  }
}

function previous() {
  if (page.value > 0) {
    page.value--
    loadTransactions()
  }
}

function next() {
  if (page.value + 1 < totalPages.value) {
    page.value++
    loadTransactions()
  }
}

function logout() {
  sessionStorage.removeItem('authenticated')
  router.push('/login')
}

onMounted(() => {
  if (sessionStorage.getItem('authenticated') !== 'true') {
    router.push('/login')
    return
  }

  loadTransactions()
})
</script>
