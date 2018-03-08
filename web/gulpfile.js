const gulp = require('gulp');
const sass = require('gulp-sass');
// const rename = require('gulp-rename');
const concat = require('gulp-concat');
// const babel = require('gulp-babel');

//sass
const global_sass_files = ['node_modules/bootstrap-4-grid/scss/grid.scss', 'src/main/assets/sass/site.scss'];
gulp.task('sass', function() {
    return gulp.src(global_sass_files)
        .pipe(sass())
        .on('error', onError)
        .pipe(concat('default.css'))
        .pipe(gulp.dest('src/main/webapp/resources/default/css'))
        // .pipe(gulp.dest('target/web/resources/default/css'))
});


//watch
gulp.task('watch', function () {
    // gulp.watch('assets/sass/**/*.scss', ['sass']);
    // gulp.watch('assets/js/**/*.js', ['js']);
});

//default
gulp.task('default', ['sass']);
// gulp.task('default-watch', ['sass', 'watch']);


function onError(err) {
    console.log(err);
    this.emit('end');
}
