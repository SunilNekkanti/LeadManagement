
var gulp        = require('gulp');
var browserSync  = require('browser-sync') ;
var freemarker = require("gulp-freemarker");

// Static Server + watching scss/html files
gulp.task('serve', ['css', 'js', 'ftl'], function() {
    browserSync.init({
        reloadDelay: 1000,
        proxy: {
        target: "localhost:8080/LeadMangement/", // can be [virtual host, sub-directory, localhost with port]
        ws: true ,// enables websockets
        proxyReq: [
          function(proxyReq) {
            proxyReq.setHeader('X-Special-Proxy-Header', 'foobar');
            console.log('proxy request');
          }
        ]
        }
    });

    gulp.watch("src/main/resources/static/templates/*.ftl", ['ftl']).on('change', browserSync.reload);
    gulp.watch("src/main/resources/static/css/*.css", ['css']).on('change', browserSync.reload);
    gulp.watch("src/main/resources/static/js/app/*.js", ['js']).on('change', browserSync.reload);
});

gulp.task('ftl', function() {
    return gulp.src("src/main/resources/static/templates/*.ftl")
        .pipe(gulp.dest("target/classes/static/templates")).pipe(browserSync.reload({
							      stream: true
							    })) ;  ;
});

// Compile sass into CSS & auto-inject into browsers
gulp.task('css', function() {
    return gulp.src("src/main/resources/static/css/*.css")
        .pipe(gulp.dest("target/classes/static/css"))
        .pipe(browserSync.reload({
							      stream: true
							    })) ;
});

gulp.task('js', function() {
    return gulp.src("src/main/resources/static/js/app/*.js")
        .pipe(gulp.dest("target/classes/static/js/app"))
        .pipe(browserSync.reload({
							      stream: true
							    })) ;
});

gulp.src("./mock/*.json")
    .pipe(freemarker({
        viewRoot: "src/main/resources/static/templates/",
        options: {}
    }))
    .pipe(gulp.dest("target/classes/static/templates/"));



gulp.task('default', ['serve']);
