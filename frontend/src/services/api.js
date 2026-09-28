const API1 = import.meta.env.VITE_API1_URL
const API2 = import.meta.env.VITE_API2_URL

async function parseResponse(response) {
  const data = await response.json().catch(() => ({}))
  if (!response.ok) {
    throw new Error(data.mensaje || 'Error en la petición')
  }
  return data
}

export async function login(username, password) {
  const response = await fetch(`${API2}/api/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  })
  return parseResponse(response)
}

export async function crearOperacion(payload) {
  const response = await fetch(`${API1}/api/operaciones`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  })
  return parseResponse(response)
}

export async function listarTransacciones(params) {
  const query = new URLSearchParams(params)
  const response = await fetch(`${API2}/api/transacciones?${query}`)
  return parseResponse(response)
}

export async function cancelarTransaccion(id) {
  const response = await fetch(`${API2}/api/transacciones/${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ estatus: 'cancelar' })
  })
  return parseResponse(response)
}
