module.exports = function(grunt) {
    require("load-grunt-tasks")(grunt);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        babel: {
            options: {
                sourceMap: true,
                presets: ['@babel/preset-env']
            },
            dist: {
                files: [{
                    expand: true,
                    cwd: "WebContent/js/app/component",
                    src: ['**/*.js'],
                    dest: "WebContent/js/es5/component",
                    ext: ".js"
                }]
            }
        },

        //for using export/import - only for app/pages folder, exclude lib folder
        browserify: {
            options: {
                browserifyOptions: { debug: true },
                transform: [["babelify", { "presets": ["@babel/preset-env"] }]],
            },
            dynamic_mappings: {
                files: [{
                    expand: true,
                    cwd: 'WebContent/js/app/pages',
                    src: ["**/*.js"],
                    dest: 'WebContent/js/es5/pages',
                    ext: '.js'
                }]
            }
        },

        /*uglify: {
            lib: {
                files: [{
                    expand: true,
                    cwd:  'WebContent/app/lib',
                    src: ["**!/!*.js"],
                    dest: 'WebContent/js/lib',
                    ext: ".min.js",
                    extDot: 'last'
                }]
            },
            build: {
                files: [{
                    expand: true,
                    cwd:  'WebContent/es5',
                    src: ["**!/!*.js"],
                    dest: 'WebContent/js',
                    ext: ".js"
                }]
            }
        },*/

        //watch for all .js files, exclude folder lib - that containes plugins (they will be minified)
        watch : {
            scripts: {
                files: ["WebContent/app/**/*.js"],
                tasks: ["changed:babel:dist", "browserify" /*"changed:uglify:build"*/ ]
            }
        }
    });

    grunt.loadNpmTasks('grunt-babel');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-changed');
    grunt.loadNpmTasks("grunt-browserify");
    grunt.loadNpmTasks("grunt-contrib-watch");

    grunt.registerTask("watchChanges", ["watch"]);
    grunt.registerTask("min", ["uglify:lib"]);

    grunt.registerTask("default", ["changed:babel:dist", "browserify" /*"uglify"*/]);
};