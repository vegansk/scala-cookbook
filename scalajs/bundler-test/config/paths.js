var path = require('path');

var rootDir = path.dirname(__dirname);

function outDir(prod) {
  return path.join(rootDir, "build", prod ? "release" : "debug");
}

function jsDir(prod) {
  return path.join(outDir(prod), 'js');
}

module.exports = {
  rootDir: rootDir,
  outDir: outDir,
  jsDir: jsDir
}
