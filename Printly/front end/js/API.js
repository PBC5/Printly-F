const API_URL = 'http://localhost:8080/api';

export async function registrarUsuario(userData) {
    try {
        const response = await fetch(`${API_URL}/usuarios/registro`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nombre: userData.nombre,
                email: userData.email,
                password: userData.password
            })
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Error al registrar usuario');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}