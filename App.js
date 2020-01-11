/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {ScrollView, Button,Platform,Text,StatusBar} from 'react-native';
import LYAdBannerView from "./src/LYAdBannerView";

class App extends React.Component {

  constructor(props) {
    super(props);
    this.initialize()
  }

  initialize = () => {

    if(Platform.OS === 'ios'){
      this.appId = "1105344611";
      this.bannerAdId = "1080958885885321";
      this.splashAdId = "9040714184494018";
      this.interstitialAdId = "1050652855580392";
      this.rewardedVideoAdId = "8020744212936426";
      this.feedAdId = "1020922903364636"

    }else {
      this.appId = "1101152570";
      this.bannerAdId = "9079537218417626401";
      this.splashAdId = "8863364436303842593";
      this.interstitialAdId = "8575134060152130849";
      this.feedAdId = "6040749702835933"
    }
    this.state={
      isShow:false
    }
  };
  componentWillMount(){
    StatusBar.setHidden(false)
  }
  componentDidMount() {

  }

  render() {
    return (
        <ScrollView>
          {this.state.isShow?
          <Button title={'12'} onPress={()=>{

          }}/>:null}
          <Button title={'你好'} onPress={()=>{
            this.setState({
              isShow:!this.state.isShow
            })
          }}/>
          <LYAdBannerView
              style={{borderWidth:1}}
          />
          <Text>HAO</Text>

        </ScrollView>
    );
  }




}

export default App;
