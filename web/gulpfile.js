var gulp = require('gulp');
var sass = require('gulp-sass');
var rename = require('gulp-rename');
var concat = require('gulp-concat');
var deporder = require('gulp-deporder');
// var newer = require('gulp-newer');
// var babel = require('gulp-babel');
var sourcemaps = require('gulp-sourcemaps');
// var stripdebug = require('gulp-strip-debug');
// var uglify = require('gulp-uglify');

// sass compile
gulp.task('sass', function() {
    return gulp.src('src/main/assets/sass/site.scss')
        .pipe(sass({errLogToConsole: true}))
        .on('error', onError)
        .pipe(rename('default.css'))
        .pipe(gulp.dest('target/web/resources/default/css'))
});

// watch
gulp.task('watch', function () {
    gulp.watch('src/main/assets/sass/**/*.scss', ['sass']);
    gulp.watch('src/main/assets/js/**/*.js', ['js']);
});

function onError(err) {
    console.log(err);
    this.emit('end');
}

// scripts
gulp.task('js', function () {
    return gulp.src('src/main/assets/js/**/*.js')
        .pipe(deporder())
        .pipe(concat('default.js'))
        // .pipe(stripdebug())
        // .pipe(uglify())
        .pipe(gulp.dest('target/web/resources/default/js'));
});

//default
gulp.task('build', ['js', 'sass']);
gulp.task('dev', ['js', 'sass', 'watch']);