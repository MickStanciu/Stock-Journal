const gulp = require('gulp');
const sass = require('gulp-sass');
const rename = require('gulp-rename');
const concat = require('gulp-concat');

//vue

//sass
const global_sass_files = ['node_modules/bootstrap-4-grid/scss/grid.scss', 'src/assets/sass/main.scss'];
gulp.task('sass', function() {
  return gulp.src(global_sass_files)
    .pipe(sass())
    .on('error', onError)
    .pipe(concat('default.css'))
    .pipe(gulp.dest('../src/main/webapp/resources/default/css'))
});


gulp.task('sass2', function() {
  return gulp.src(['./foo/foo.scss', './bar/bar.scss'], { base: '.' })
    .pipe(sass())
    .pipe(gulp.dest('.'));
});

//default
gulp.task('default', ['sass']);


function onError(err) {
  console.log(err);
  this.emit('end');
}
