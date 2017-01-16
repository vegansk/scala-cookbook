var path = require('path');

var rootDir = path.dirname(__dirname);

function outDir(prod) {
  return path.join(rootDir, "build", prod ? "release" : "debug");
}

function jsDir(prod) {
  return path.join(outDir(prod), 'js');
}

var paths = {
  rootDir: rootDir,
  assetsDir: path.join(rootDir, 'src', 'main', 'assets'),
  outDir: outDir,
  jsDir: jsDir
}

function doConfig(cfg, prod, imports) {
  cfg.output.path = paths.outDir(prod);
  cfg.output.filename = path.join('js', 'index.js');

  cfg.module = cfg.module || {};
  cfg.module.loaders = cfg.module.loaders || [];
  cfg.module.loaders.push({
    test: /\.html$/,
    loader: 'html'
  });

  cfg.plugins = cfg.plugins || [];
  cfg.plugins.push(new imports.HtmlWebpackPlugin({
    template: path.join(paths.assetsDir, 'index.html')
  }));

  return cfg;
}

module.exports = {
  doConfig: doConfig
};
