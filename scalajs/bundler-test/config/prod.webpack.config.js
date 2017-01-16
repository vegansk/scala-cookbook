var HtmlWebpackPlugin = require('html-webpack-plugin');
var path = require('path');
var rootDir = path.dirname(path.dirname(path.dirname(path.dirname(__dirname))));
var common = require(path.join(rootDir, 'config', 'common.webpack.config.js'));

var cfg = require('./scalajs.webpack.config');

module.exports = common.doConfig(cfg, true, imports = {
  HtmlWebpackPlugin: HtmlWebpackPlugin
});
