document.addEventListener('DOMContentLoaded', (event) => {
    console.log('The DOM is fully loaded and parsed');
    const messageElement = document.getElementById('message');
    if (messageElement) {
        messageElement.textContent = 'Hello, Kotlin!';
    }
});