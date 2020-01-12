
import React, { Component } from 'react';
import {
    requireNativeComponent,
    ViewPropTypes,
    Platform,
    Dimensions,
} from 'react-native';
import { string, func } from 'prop-types';

const WIDTH = Dimensions.get('window').width;
const height = WIDTH/6;
class LYAdBannerView extends Component {

    constructor() {
        super();
        this.handleAdFailedToLoad = this.handleAdFailedToLoad.bind(this);
        this.handleSizeChange = this.handleSizeChange.bind(this);
        this.state = {
            style: {width:WIDTH, height:height},
        };
        this.isShow = false
    }

    handleAdFailedToLoad(event) {
        if (this.props.onNoAD) {
            this.props.onNoAD(createErrorFromErrorData(event.nativeEvent.error));
        }
    }

    componentDidMount() {
        this.timer = setInterval(()=>{
            this.isShow=!this.isShow
            if(this.isShow){
                this.setState({ style: { width:WIDTH, height:height-1 } });
            }else {
                this.setState({ style: { width:WIDTH, height:height } });
            }
            // console.warn('componentDidMount')
        },2000)
    }

    componentWillUnmount() {
        this.timer&&clearInterval(this.timer)
    }

    handleSizeChange(event) {
        const { height, width } = event.nativeEvent;
        let bannerWidth = Platform.OS === 'ios'?width:WIDTH;
        this.setState({ style: { width:WIDTH, height:height } });
        if (this.props.onSizeChange) {
            this.props.onSizeChange({ width, height });
        }
        console.warn('123')
    }

    render() {
        return (
            <RNTLYAdBannerView
                {...this.props}
                style={[this.props.style,{alignSelf:'center'},this.state.style]}
                onNoAD={this.handleAdFailedToLoad}
                onSizeChange={this.handleSizeChange}
            />
        );
    }
}

LYAdBannerView.propTypes = {
    ...ViewPropTypes,
    placementId: string,
    onADReceiv: func,
    onADClicked: func,
    onADClosed: func,
    onADLeftApplication: func,
    onADExposure: func,
    onNoAD: func,
    onSizeChange:func
};

const RNTLYAdBannerView = requireNativeComponent('LYAdBannerView', LYAdBannerView);

export default LYAdBannerView;

export const createErrorFromErrorData = (errorData) => {
    const {
        message,
        ...extraErrorInfo
    } = errorData || {};
    const error = new Error(message);
    error.framesToPop = 1;
    return Object.assign(error, extraErrorInfo);
}
