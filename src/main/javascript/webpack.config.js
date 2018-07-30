let path = require('path');
let webpack = require('webpack');
let DEST = path.resolve(__dirname, '../webapp/resources/dist');
let ENTRY = './app/main.js';

let config = {
    watch: true,
    devtool: 'source-map',
    entry: ENTRY,
    output: {
        path: DEST,
        filename: 'bundle.js',
        publicPath: '/dist/',
        libraryTarget: 'var',
        library: "Battleship"
    },
    node: {
        fs: 'empty'
    },
    target: 'web'
};

module.exports = (env, argv) => {
    if (argv.mode === 'development') {
        config.watch = true;
    }
    if (argv.mode === 'production') {
        config.watch = false;
    }
    return config;
};
