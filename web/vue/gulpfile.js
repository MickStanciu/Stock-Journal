const gulp = require('gulp');
const sass = require('gulp-sass');
const rename = require('gulp-rename');
const concat = require('gulp-concat');

//sass
const global_sass_files = ['node_modules/bootstrap-4-grid/scss/grid.scss', 'src/assets/sass/main.scss'];
gulp.task('sass', function() {
  return gulp.src(global_sass_files)
    .pipe(sass())
    .on('error', onError)
    .pipe(concat('default.css'))
    .pipe(gulp.dest('../src/main/webapp/resources/default/css'))
});

//vue
gulp.task('vue', function () {

});

//watch
gulp.task('watch', function () {
  // gulp.watch('assets/sass/**/*.scss', ['sass']);
  // gulp.watch('assets/js/**/*.js', ['js']);
});

//default
gulp.task('default', ['sass', 'vue']);
gulp.task('default-watch', ['sass', 'vue', 'watch']);


function onError(err) {
  console.log(err);
  this.emit('end');
}
