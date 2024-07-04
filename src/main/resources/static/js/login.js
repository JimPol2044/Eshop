// Wait for the DOM content to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Select the login form element
    const loginForm = document.getElementById('loginForm');

    // Add an event listener for the form submission
    loginForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Get the username and password from the form
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Perform client-side validation if needed

        // Prepare the login request data
        const loginData = {
            username: username,
            password: password
        };

        // Send a POST request to the server with loginData
        fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        })
            .then(response => {
                if (response.ok) {
                    // Successful login logic (e.g., redirect to dashboard)
                    window.location.href = '/dashboard'; // Redirect to dashboard page
                } else {
                    // Handle unsuccessful login (display error message, etc.)
                    console.error('Login failed:', response.statusText);
                    alert('Login failed. Please check your credentials.');
                }
            })
            .catch(error => {
                console.error('Error during login:', error);
                alert('An unexpected error occurred during login.');
            });
    });
});
