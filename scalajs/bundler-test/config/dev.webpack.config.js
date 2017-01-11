var webpack = require('webpack');
var path = require('path');
var rootDir = path.dirname(path.dirname(path.dirname(path.dirname(__dirname))));
var paths = require(path.join(rootDir, 'config', 'paths.js'));

var cfg = require('./scalajs.webpack.config');

cfg.output.path = paths.jsDir(false);
cfg.output.filename = 'index.js';

module.exports = cfg;
