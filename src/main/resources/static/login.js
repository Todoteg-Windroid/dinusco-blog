jQuery(document).ready(function($) {
	tab = $('.tabs h3 a');

	tab.on('click', function(event) {
		event.preventDefault();
		
		// Obtener la URL desde el atributo "data-url" de la pestaña seleccionada
        var url = $(this).data('url');

        // Redirigir a la URL correspondiente
        window.location.href = url;
	});
	
	// Obtener la ruta actual
    var currentPath = window.location.pathname;

    if (currentPath === "/login") {
        // Si la ruta es /login, establecer el formulario de inicio de sesión como activo
        $('.login-tab a').addClass('active');
        $('#login-tab-content').addClass('active');
    } else if (currentPath === "/signup") {
        // Si la ruta es /signup, establecer el formulario de registro como activo
        $('.signup-tab a').addClass('active');
        $('#signup-tab-content').addClass('active');
    }
});