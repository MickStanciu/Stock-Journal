const gulp = require('gulp');
const sass = require('gulp-sass');
const rename = require('gulp-rename');
//vue

//sass
const global_sass_files = ['node_modules/bootstrap-4-grid/scss/grid.scss', 'src/assets/sass/main.scss'];
gulp.task('sass', function() {
  return gulp.src(global_sass_files, { base: '.' })
    .pipe(sass())
    .on('error', onError)
    // .pipe(rename('default.css'))
    .pipe(gulp.dest('.'))
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
