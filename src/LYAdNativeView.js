
import React, { Component } from 'react';
import {
    requireNativeComponent,
    ViewPropTypes,
    Platform,
    Dimensions,
} from 'react-native';
import { string, func } from 'prop-types';

const WIDTH = Dimensions.get('window').width;
const height = Dimensions.get('window').height;

class LYAdNativeView extends Component {

    constructor() {
        super();
        this.handleAdFailedToLoad = this.handleAdFailedToLoad.bind(this);
        this.handleSizeChange = this.handleSizeChange.bind(this);
        this.state = {
            style: {width:WIDTH, height:WIDTH/3*2},
        };
    }

    handleAdFailedToLoad(event) {
        if (this.props.onNoAD) {
            this.props.onNoAD(createErrorFromErrorData(event.nativeEvent.error));
        }
    }

    // componentDidMount() {
    //     this.timer = setInterval(()=>{
    //         this.isShow=!this.isShow
    //         if(this.isShow){
    //             this.setState({ style: { width:WIDTH, height:WIDTH/3*2-1 } });
    //         }else {
    //             this.setState({ style: { width:WIDTH, height:WIDTH/3*2 } });
    //         }
    //         // console.warn('componentDidMount')
    //     },2000)
    // }
    //
    // componentWillUnmount() {
    //     this.timer&&clearInterval(this.timer)
    // }

    handleSizeChange(event) {
        const { height, width } = event.nativeEvent;
        this.setState({ style: { width:WIDTH, height:height/width*WIDTH } });
        if (this.props.onSizeChange) {
            this.props.onSizeChange({ width, height });
        }
        console.warn('height',height,'width',width)
    }

    render() {
        return (
            <RNTLYAdNativeView
                {...this.props}
                style={[this.props.style,{alignSelf:'center'},this.state.style]}
                // onNoAD={this.handleAdFailedToLoad}
                onNativeSizeChange={this.handleSizeChange}
            />
        );
    }
}

LYAdNativeView.propTypes = {
    ...ViewPropTypes,
    placementId: string,
    // onADReceiv: func,
    // onADClicked: func,
    // onADClosed: func,
    // onADLeftApplication: func,
    // onADExposure: func,
    // onNoAD: func,
    onNativeSizeChange:func
};

const RNTLYAdNativeView = requireNativeComponent('LYAdNativeView', LYAdNativeView);

export default LYAdNativeView;

export const createErrorFromErrorData = (errorData) => {
    const {
        message,
        ...extraErrorInfo
    } = errorData || {};
    const error = new Error(message);
    error.framesToPop = 1;
    return Object.assign(error, extraErrorInfo);
}
