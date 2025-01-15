function rowClick(event, url) {
    if (!event.target.closest('a')) {
        window.location.href = url;
    }
}