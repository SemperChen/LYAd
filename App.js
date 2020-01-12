/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {ScrollView} from 'react-native';
import LYAdBannerView from "./src/LYAdBannerView";
import LYAdNativeView from "./src/LYAdNativeView";

class App extends React.Component {

  render() {
    return (
        <ScrollView>

          <LYAdBannerView/>


          <LYAdNativeView/>


        </ScrollView>
    );
  }

}

export default App;
