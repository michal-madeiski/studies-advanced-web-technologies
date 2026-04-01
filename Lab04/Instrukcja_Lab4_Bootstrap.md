# Lab 4 – Single Page Application z Bootstrap 4 i Material Design Bootstrap (MDB)

> **Przedmiot:** Zaawansowane Technologie Webowe
> **Temat:** Opracowanie szablonu Single Page z Bootstrap 4 oraz Material Design
> **Czas:** 2 godziny

---

## Wymagania zadania (ze zrzutu ekranu z kursu)

Używając Bootstrap (4 lub 5) i Material Design należy rozwinąć przykład opisany w materiałach bazowych. Strona musi zawierać **co najmniej**:

- Sekcję z formularzem kontaktowym
- Sekcję z Google Maps
- Sekcję z tekstem i zdjęciem w stosunku 1 do 3
- Sekcję z cytatem
- ~~Sekcję z listą pracowników z Multi-item-carousel~~ *(wycofane, wymaga płatnej licencji)*
- **Sekcję z kontrolką Carousel** – dla wyższej oceny połączyć z kontrolkami Card wyświetlającymi listę pracowników
- **Sekcję FAQ** z wykorzystaniem kontrolki Accordion
- **Sekcję z wykresem** najpopularniejszych kierunków podróży

> **Wyższa ocena:** wzbogacić szablon o animacje (np. efekty WOW.js lub Animate.css).

---

## Krok 1 – Pobranie i konfiguracja projektu

### 1.1 Pobierz pakiet MDB (Material Design Bootstrap)

Wejdź na stronę: https://mdbootstrap.com/docs/jquery/getting-started/download/

Pobierz **darmową wersję** (plik `.zip`). Wypakuj archiwum – znajdziesz w środku gotową strukturę folderów:

```
css/
  bootstrap.min.css
  mdb.min.css
  style.css
js/
  jquery.min.js
  popper.min.js
  bootstrap.min.js
  mdb.min.js
img/
index.html
```

> Plik `index.html` będzie bazą Twojego projektu.

---

## Krok 2 – Podlinkowanie bibliotek w `index.html`

Otwórz `index.html`. W sekcji `<head>` powinny znajdować się linki do CSS:

```html
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- Material Design Bootstrap -->
<link rel="stylesheet" href="css/mdb.min.css">
<!-- Your custom styles -->
<link rel="stylesheet" href="css/style.css">
```

Na **końcu sekcji `<body>`** dodaj skrypty JS (kolejność jest ważna!):

```html
<!-- jQuery -->
<script type="text/javascript" src="js/jquery.min.js"></script>
<!-- Bootstrap tooltips -->
<script type="text/javascript" src="js/popper.min.js"></script>
<!-- Bootstrap core JavaScript -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<!-- MDB core JavaScript -->
<script type="text/javascript" src="js/mdb.min.js"></script>
<!-- Your custom scripts -->
<script type="text/javascript"></script>
```

> ⚠️ Ważne: `css/mdb.min.css` musi być **przed** `css/bootstrap.min.css`, a `js/jquery.min.js` musi być **przed** `js/bootstrap.min.js`.

Treść strony umieszczamy **między komentarzami**:
```html
<!-- Start your project here-->
...
<!-- End your project here-->
```

---

## Krok 3 – Struktura strony i nawigacja

### 3.1 Główna hierarchia HTML

Między komentarzami `Start/End your project here` wklej:

```html
<!--Main Navigation-->
<header>

</header>
<!--Main Navigation-->

<!--Main layout-->
<main class="mt-5">
  <div class="container">
    <!--Section: LastTravels-->
    <section id="lastTravels">
    </section>
    <!--Section: LastTravels-->

    <hr class="my-5">

    <!--Section: Carousel-->
    <section id="carousel">
    </section>
    <!--Section: Carousel-->

    <hr class="my-5">

    <!--Section: TextImage-->
    <section id="textImage">
    </section>
    <!--Section: TextImage-->

    <hr class="my-5">

    <!--Section: Quote-->
    <section id="quote">
    </section>
    <!--Section: Quote-->

    <hr class="my-5">

    <!--Section: FAQ-->
    <section id="faq">
    </section>
    <!--Section: FAQ-->

    <hr class="my-5">

    <!--Section: Chart-->
    <section id="chart">
    </section>
    <!--Section: Chart-->

    <hr class="my-5">

    <!--Section: Gallery-->
    <section id="gallery">
    </section>
    <!--Section: Gallery-->

    <hr class="my-5">

    <!--Section: Contact-->
    <section id="contact">
    </section>
    <!--Section: Contact-->
  </div>
</main>
<!--Main layout-->

<!--Footer-->
<footer>

</footer>
<!--Footer-->
```

### 3.2 Pasek nawigacji (Navbar)

Wklej wewnątrz `<header>`:

```html
<!--Navbar-->
<nav class="navbar navbar-expand-lg navbar-dark primary-color fixed-top">

  <div class="container">

    <!-- Navbar brand -->
    <a class="navbar-brand" href="#">Podróżowanie</a>

    <!-- Collapse button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#mainNav"
        aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Collapsible content -->
    <div class="collapse navbar-collapse" id="mainNav">

      <!-- Links -->
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="#intro">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#lastTravels">Podróże</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#faq">FAQ</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#contact">Kontakt</a>
        </li>
      </ul>

      <!-- Search form -->
      <form class="form-inline">
        <div class="md-form my-0">
          <input class="form-control mr-sm-2" type="text" placeholder="Szukaj" aria-label="Search">
        </div>
      </form>
    </div>
    <!-- Collapsible content -->

  </div>

</nav>
<!--/.Navbar-->
```

> Klasa `.fixed-top` sprawia, że menu jest zawsze przyklejone na górze ekranu (sticky).

---

## Krok 4 – Landing page (pełnoekranowe tło)

### 4.1 HTML tła

Dodaj zaraz **za tagiem `</nav>`** (wciąż w `<header>`):

```html
<!--Mask-->
<div id="intro" class="view">
  <div class="mask rgba-stylish-strong">
    <div class="container-fluid d-flex align-items-center justify-content-center h-100">
      <div class="row d-flex justify-content-center text-center">
        <div class="col-md-10">

          <!-- Heading -->
          <h2 class="display-4 font-weight-bold white-text pt-5 mb-2">Podróżowanie</h2>

          <!-- Divider -->
          <hr class="hr-light">

          <!-- Description -->
          <h4 class="white-text my-4">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Deleniti consequuntur.</h4>

          <a type="button" class="btn btn-outline-white">Dowiedz się więcej <i class="fas fa-book ml-2"></i></a>

        </div>
      </div>
    </div>
  </div>
</div>
<!--/.Mask-->
```

### 4.2 CSS dla tła

Otwórz plik `css/style.css` i wklej:

```css
html,
body,
header,
#intro {
    height: 100%;
}

#intro {
    background: url("../img/bg.jpg") no-repeat center center fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
}
```

> Do folderu `img/` wgraj zdjęcie tła o rozdzielczości co najmniej **1920x1280px** i nazwij je `bg.jpg`.

Maska `.rgba-stylish-strong` przyciemnia tło dla lepszej czytelności tekstu. Możesz wypróbować inne maski z dokumentacji: https://mdbootstrap.com/docs/jquery/css/masks/

---

## Krok 5 – Sekcja z kafelkami (Grid – ostatnie podróże)

Wewnątrz `<section id="lastTravels">` wklej:

```html
<section id="lastTravels" class="text-center">
  <!--Grid row-->
  <div class="row">

    <!--Grid column-->
    <div class="col-lg-4 col-md-12 mb-4">
      <div class="view overlay z-depth-1-half">
        <img src="img/trip1.jpg" class="img-fluid" alt="">
        <a href="#!">
          <div class="mask rgba-blue-light"></div>
        </a>
      </div>
      <h4 class="my-4 font-weight-bold">Podróż 1</h4>
      <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam, aperiam minima assumenda deleniti hic.</p>
    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="col-lg-4 col-md-6 mb-4">
      <div class="view overlay z-depth-1-half">
        <img src="img/trip2.jpg" class="img-fluid" alt="">
        <a href="#!">
          <div class="mask rgba-blue-light"></div>
        </a>
      </div>
      <h4 class="my-4 font-weight-bold">Podróż 2</h4>
      <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam, aperiam minima assumenda deleniti hic.</p>
    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="col-lg-4 col-md-6 mb-4">
      <div class="view overlay z-depth-1-half">
        <img src="img/trip3.jpg" class="img-fluid" alt="">
        <a href="#!">
          <div class="mask rgba-blue-light"></div>
        </a>
      </div>
      <h4 class="my-4 font-weight-bold">Podróż 3</h4>
      <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam, aperiam minima assumenda deleniti hic.</p>
    </div>
    <!--Grid column-->

  </div>
  <!--Grid row-->

  <!--Grid row-->
  <div class="row">

    <!--Grid column-->
    <div class="col-lg-4 col-md-12 mb-4">
      <div class="view overlay z-depth-1-half">
        <img src="img/trip4.jpg" class="img-fluid" alt="">
        <a href="#!">
          <div class="mask rgba-blue-light"></div>
        </a>
      </div>
      <h4 class="my-4 font-weight-bold">Podróż 4</h4>
      <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam, aperiam minima assumenda deleniti hic.</p>
    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="col-lg-4 col-md-6 mb-4">
      <div class="view overlay z-depth-1-half">
        <img src="img/trip5.jpg" class="img-fluid" alt="">
        <a href="#!">
          <div class="mask rgba-blue-light"></div>
        </a>
      </div>
      <h4 class="my-4 font-weight-bold">Podróż 5</h4>
      <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam, aperiam minima assumenda deleniti hic.</p>
    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="col-lg-4 col-md-6 mb-4">
      <div class="view overlay z-depth-1-half">
        <img src="img/trip6.jpg" class="img-fluid" alt="">
        <a href="#!">
          <div class="mask rgba-blue-light"></div>
        </a>
      </div>
      <h4 class="my-4 font-weight-bold">Podróż 6</h4>
      <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam, aperiam minima assumenda deleniti hic.</p>
    </div>
    <!--Grid column-->

  </div>
  <!--Grid row-->
</section>
```

> Do folderu `img/` wgraj 6 zdjęć (~800x600px) o nazwach `trip1.jpg` do `trip6.jpg`.
> Klasa `.z-depth-1-half` dodaje cień pod obrazkiem. Klasa `.rgba-blue-light` tworzy efekt hover po najechaniu kursorem.

---

## Krok 6 – Sekcja Carousel z kartami pracowników

Wewnątrz `<section id="carousel">` wklej:

```html
<h2 class="h1-responsive font-weight-bold text-center my-5">Nasz zespół</h2>

<div id="teamCarousel" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner" role="listbox">

    <!-- Item 1 (active) -->
    <div class="carousel-item active">
      <div class="row">

        <div class="col-md-4">
          <div class="card mb-2">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Anna Kowalska</h5>
              <h6 class="card-subtitle mb-2 text-muted">Przewodnik turystyczny</h6>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card mb-2">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Jan Nowak</h5>
              <h6 class="card-subtitle mb-2 text-muted">Fotograf</h6>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card mb-2">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Marta Wiśniewska</h5>
              <h6 class="card-subtitle mb-2 text-muted">Koordynator tras</h6>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
            </div>
          </div>
        </div>

      </div>
    </div>
    <!-- Item 1 -->

    <!-- Item 2 -->
    <div class="carousel-item">
      <div class="row">

        <div class="col-md-4">
          <div class="card mb-2">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Piotr Zając</h5>
              <h6 class="card-subtitle mb-2 text-muted">Kierowca</h6>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card mb-2">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Karolina Dąbrowska</h5>
              <h6 class="card-subtitle mb-2 text-muted">Specjalista ds. rezerwacji</h6>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card mb-2">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Tomasz Lewandowski</h5>
              <h6 class="card-subtitle mb-2 text-muted">Manager</h6>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
            </div>
          </div>
        </div>

      </div>
    </div>
    <!-- Item 2 -->

  </div>

  <!-- Controls -->
  <a class="carousel-control-prev" href="#teamCarousel" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Poprzedni</span>
  </a>
  <a class="carousel-control-next" href="#teamCarousel" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Następny</span>
  </a>
</div>
```

---

## Krok 7 – Sekcja tekst + zdjęcie (stosunek 1:3)

Wewnątrz `<section id="textImage">` wklej:

```html
<h2 class="h1-responsive font-weight-bold text-center my-5">O nas</h2>

<div class="row align-items-center">

  <!-- Tekst (3/4 szerokości) -->
  <div class="col-md-9">
    <h3 class="font-weight-bold mb-3">Odkrywamy świat razem</h3>
    <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas volutpat, diam enim sagittis quam, id porta quam. Sed id dolor consectetur fermentum nibh volutpat, accumsan purus.</p>
    <p class="grey-text">Nunc nulla libero, convallis sed ante in, lacinia dictum est. Fusce porta, metus blandit tincidunt ornare, enim odio aliquam turpis, non rhoncus ipsum nisi a tortor.</p>
    <a class="btn btn-primary btn-md">Czytaj więcej</a>
  </div>

  <!-- Zdjęcie (1/4 szerokości) -->
  <div class="col-md-3">
    <img src="img/trip1.jpg" class="img-fluid z-depth-1" alt="O nas">
  </div>

</div>
```

> Stosunek kolumn `col-md-9` (tekst) do `col-md-3` (zdjęcie) daje proporcję 3:1 (lub 1:3 — zależy od kolejności).

---

## Krok 8 – Sekcja z cytatem

Wewnątrz `<section id="quote">` wklej:

```html
<div class="jumbotron text-center primary-color white-text">
  <h2 class="card-title h1-responsive pt-3 mb-5 font-weight-bold">
    <i class="fas fa-quote-left mr-3"></i>
    Świat jest książką, a ci, którzy nie podróżują, czytają tylko jedną stronę.
    <i class="fas fa-quote-right ml-3"></i>
  </h2>
  <p class="font-weight-bold">– Święty Augustyn</p>
</div>
```

---

## Krok 9 – Sekcja FAQ z Accordion

Wewnątrz `<section id="faq">` wklej:

```html
<h2 class="h1-responsive font-weight-bold text-center my-5">FAQ – Najczęstsze pytania</h2>

<div id="accordionFAQ">

  <div class="card">
    <div class="card-header" id="heading1">
      <h5 class="mb-0">
        <button class="btn btn-link" data-toggle="collapse" data-target="#collapse1" aria-expanded="true" aria-controls="collapse1">
          Jak zarezerwować wycieczkę?
        </button>
      </h5>
    </div>
    <div id="collapse1" class="collapse show" aria-labelledby="heading1" data-parent="#accordionFAQ">
      <div class="card-body">
        Wycieczki można zarezerwować przez formularz kontaktowy na stronie, telefonicznie lub osobiście w naszym biurze.
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-header" id="heading2">
      <h5 class="mb-0">
        <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapse2" aria-expanded="false" aria-controls="collapse2">
          Czy oferujecie ubezpieczenie podróżne?
        </button>
      </h5>
    </div>
    <div id="collapse2" class="collapse" aria-labelledby="heading2" data-parent="#accordionFAQ">
      <div class="card-body">
        Tak, w każdym pakiecie wycieczki dostępna jest opcja wykupienia ubezpieczenia podróżnego.
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-header" id="heading3">
      <h5 class="mb-0">
        <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapse3" aria-expanded="false" aria-controls="collapse3">
          Jaki jest termin płatności?
        </button>
      </h5>
    </div>
    <div id="collapse3" class="collapse" aria-labelledby="heading3" data-parent="#accordionFAQ">
      <div class="card-body">
        Zaliczkę w wysokości 30% należy wpłacić w ciągu 7 dni od rezerwacji. Pozostałą kwotę – najpóźniej 30 dni przed wyjazdem.
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-header" id="heading4">
      <h5 class="mb-0">
        <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapse4" aria-expanded="false" aria-controls="collapse4">
          Czy można anulować wycieczkę?
        </button>
      </h5>
    </div>
    <div id="collapse4" class="collapse" aria-labelledby="heading4" data-parent="#accordionFAQ">
      <div class="card-body">
        Tak, jednak warunki zwrotu zależą od terminu anulowania. Szczegóły znajdziesz w regulaminie.
      </div>
    </div>
  </div>

</div>
```

---

## Krok 10 – Sekcja wykresu (najpopularniejsze kierunki podróży)

Do sekcji `<section id="chart">` dodaj najpierw bibliotekę Chart.js w `<head>`:

```html
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
```

Następnie wklej sekcję:

```html
<h2 class="h1-responsive font-weight-bold text-center my-5">Najpopularniejsze kierunki podróży</h2>

<canvas id="travelsChart" style="max-height: 400px;"></canvas>

<script>
  var ctx = document.getElementById('travelsChart').getContext('2d');
  var travelsChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ['Włochy', 'Grecja', 'Hiszpania', 'Francja', 'Portugalia', 'Chorwacja'],
      datasets: [{
        label: 'Liczba rezerwacji',
        data: [320, 280, 250, 200, 175, 160],
        backgroundColor: [
          'rgba(63, 81, 181, 0.7)',
          'rgba(33, 150, 243, 0.7)',
          'rgba(0, 188, 212, 0.7)',
          'rgba(0, 150, 136, 0.7)',
          'rgba(76, 175, 80, 0.7)',
          'rgba(255, 193, 7, 0.7)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
</script>
```

---

## Krok 11 – Sekcja formularz kontaktowy

Wewnątrz `<section id="contact">` wklej:

```html
<h2 class="h1-responsive font-weight-bold text-center my-5">Kontakt</h2>

<div class="row">

  <!-- Formularz -->
  <div class="col-md-9 mb-md-0 mb-5">
    <form id="contact-form" name="contact-form" action="mail.php" method="POST">

      <div class="row">
        <div class="col-md-6">
          <div class="md-form mb-0">
            <input type="text" id="name" name="name" class="form-control">
            <label for="name" class="">Imię i nazwisko</label>
          </div>
        </div>
        <div class="col-md-6">
          <div class="md-form mb-0">
            <input type="text" id="email" name="email" class="form-control">
            <label for="email" class="">Email</label>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="md-form mb-0">
            <input type="text" id="subject" name="subject" class="form-control">
            <label for="subject" class="">Temat</label>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="md-form">
            <textarea type="text" id="message" name="message" rows="2" class="form-control md-textarea"></textarea>
            <label for="message">Wiadomość</label>
          </div>
        </div>
      </div>

      <div class="text-center text-md-left">
        <a class="btn btn-primary" onclick="document.getElementById('contact-form').submit();">Wyślij</a>
      </div>

    </form>
  </div>
  <!-- Formularz -->

  <!-- Google Maps -->
  <div class="col-md-3 text-center">
    <iframe
      src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2505.389...!2d17.038538!3d51.107883!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x0!2zNTHCsDA2JzI4LjQiTiAxN8KwMDInMTkuNyJF!5e0!3m2!1spl!2spl!4v1234567890"
      width="100%" height="300" style="border:0;" allowfullscreen="" loading="lazy">
    </iframe>
  </div>
  <!-- Google Maps -->

</div>
```

> Wygeneruj własny embed Google Maps: wejdź na maps.google.com, wyszukaj lokalizację, kliknij **Udostępnij → Osadź mapę** i skopiuj kod `<iframe>`.

---

## Krok 12 – Stopka (Footer)

Wewnątrz `<footer>` wklej:

```html
<footer class="page-footer font-small primary-color pt-4">
  <div class="container text-center text-md-left">
    <div class="row">
      <div class="col-md-6 mt-md-0 mt-3">
        <h5 class="text-uppercase font-weight-bold">Podróżowanie.pl</h5>
        <p>Organizujemy niezapomniane wyprawy po całym świecie.</p>
      </div>
      <div class="col-md-3 mb-md-0 mb-3">
        <h5 class="text-uppercase font-weight-bold">Linki</h5>
        <ul class="list-unstyled">
          <li><a href="#intro" class="white-text">Home</a></li>
          <li><a href="#lastTravels" class="white-text">Podróże</a></li>
          <li><a href="#faq" class="white-text">FAQ</a></li>
          <li><a href="#contact" class="white-text">Kontakt</a></li>
        </ul>
      </div>
      <div class="col-md-3 mb-md-0 mb-3">
        <h5 class="text-uppercase font-weight-bold">Kontakt</h5>
        <ul class="list-unstyled">
          <li><i class="fas fa-home mr-3"></i>Wrocław, Polska</li>
          <li><i class="fas fa-envelope mr-3"></i>info@podrozowanie.pl</li>
          <li><i class="fas fa-phone mr-3"></i>+48 123 456 789</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="footer-copyright text-center py-3 white-text">
    © 2024 Podróżowanie.pl
  </div>
</footer>
```

---

## Podsumowanie – lista wymagań vs implementacja

| Wymaganie | Sekcja | Status |
|---|---|---|
| Formularz kontaktowy | `#contact` (lewa kolumna) | ✅ Krok 11 |
| Google Maps | `#contact` (prawa kolumna) | ✅ Krok 11 |
| Tekst + zdjęcie (1:3) | `#textImage` | ✅ Krok 7 |
| Cytat | `#quote` | ✅ Krok 8 |
| Carousel z kartami pracowników | `#carousel` | ✅ Krok 6 |
| FAQ z Accordion | `#faq` | ✅ Krok 9 |
| Wykres kierunków podróży | `#chart` | ✅ Krok 10 |
| Nawigacja + Landing page | `<header>` | ✅ Kroki 3-4 |
| Siatka podróży (6 kafelków) | `#lastTravels` | ✅ Krok 5 |

---

## Przydatne linki do dokumentacji MDB

- Navbar: https://mdbootstrap.com/docs/jquery/navigation/navbar/
- Grid: https://mdbootstrap.com/docs/jquery/layout/grid-usage/
- Masks: https://mdbootstrap.com/docs/jquery/css/masks/
- Hover Effects: https://mdbootstrap.com/docs/jquery/css/hover-effects/
- Carousel: https://mdbootstrap.com/docs/jquery/javascript/carousel/
- Accordion: https://mdbootstrap.com/docs/jquery/javascript/accordion/
- Cards: https://mdbootstrap.com/docs/jquery/components/cards/
- Typography: https://mdbootstrap.com/docs/jquery/content/typography/
- Icons: https://mdbootstrap.com/docs/jquery/content/icons-list/
- Shadows: https://mdbootstrap.com/docs/jquery/css/shadows/

---

Plik zapisz w folderze projektu jako `index.html` i otwórz w przeglądarce Chrome lub Firefox.

---

Powodzenia! 🚀

---

FIN TREŚCI
