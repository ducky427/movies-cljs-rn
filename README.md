# movies-cljs

This is a partial working version of the movies example in [React-Native](https://github.com/facebook/react-native/tree/master/Examples/Movies) repo.


## Usage

To run in iOS:
```
$ re-natal xcode
```
and then run your app from Xcode normally.


Start figwheel:

```
$ re-natal use-figwheel
$ lein figwheel ios
```

See [re-natal](https://github.com/drapanjanas/re-natal) for details.

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

https://github.com/decker405/figwheel-react-native
https://github.com/chendesheng/ReagentNativeDemo
https://github.com/drapanjanas/re-natal

react-native bundle --entry-file index.ios.js --platform ios --minify --bundle-output ios/main.jsbundle --dev false
