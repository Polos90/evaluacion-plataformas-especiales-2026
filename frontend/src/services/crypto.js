const encoder = new TextEncoder()

function base64(bytes) {
  let binary = ''
  bytes.forEach(byte => binary += String.fromCharCode(byte))
  return btoa(binary)
}

export async function encryptAES256GCM(value) {
  const keyText = import.meta.env.VITE_AES_KEY

  if (!keyText || encoder.encode(keyText).length !== 32) {
    throw new Error('VITE_AES_KEY debe tener exactamente 32 bytes')
  }

  const key = await crypto.subtle.importKey(
    'raw',
    encoder.encode(keyText),
    { name: 'AES-GCM' },
    false,
    ['encrypt']
  )

  const iv = crypto.getRandomValues(new Uint8Array(12))

  const encrypted = await crypto.subtle.encrypt(
    { name: 'AES-GCM', iv },
    key,
    encoder.encode(value)
  )

  const encryptedBytes = new Uint8Array(encrypted)
  const result = new Uint8Array(iv.length + encryptedBytes.length)

  result.set(iv, 0)
  result.set(encryptedBytes, iv.length)

  return base64(result)
}
