document.addEventListener('DOMContentLoaded', function() {
    const paragraphs = document.querySelectorAll('p');
    paragraphs.forEach(paragraph => {
        paragraph.addEventListener('mouseover', function() {
            this.style.color = 'pink';
        });
        paragraph.addEventListener('mouseout', function() {
            this.style.color = '';
        });
    });
});
