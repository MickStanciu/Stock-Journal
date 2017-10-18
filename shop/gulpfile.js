var gulp = require('gulp');
var sass = require('gulp-sass');
var rename = require('gulp-rename');
var concat = require('gulp-concat');
var deporder = require('gulp-deporder');
var stripdebug = require('gulp-strip-debug');
var uglify = require('gulp-uglify');

// sass compile

gulp.task('sass', function() {
    return gulp.src('assets/sass/site.scss')
        .pipe(sass({errLogToConsole: true}))
        .pipe(rename('default.css'))
        .pipe(gulp.dest('src/main/webapp/resources/default/css'));
});

// watch
gulp.task('watch', function () {
    gulp.watch('assets/sass/**/*.scss', ['sass']);
    gulp.watch('assets/js/**/*.js', ['js']);
});

// scripts
// gulp.task('js', function () {
//     return gulp.src('assets/js/**/*.js')
//         .pipe(deporder())
//         .pipe(concat('default.js'))
//         // .pipe(stripdebug())
//         .pipe(uglify())
//         .pipe(gulp.dest('src/main/webapp/resources/viator/js'));
// });

//default
gulp.task('default', ['sass', 'watch']);