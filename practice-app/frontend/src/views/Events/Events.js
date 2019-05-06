import React, { Component } from 'react';
import {
  Card,
  CardBody,
  CardHeader,
  Col,
  Row,
} from 'reactstrap';

import axios from 'axios';

class Events extends Component {
  constructor(props) {
    super(props);

    this.state = {
      events: []
    };
  }

  baseUrl = "http://52.87.206.237:8000/";

  componentDidMount(){
    axios.get(this.baseUrl+"events/")
    .then(res=>{
      var data = JSON.parse(res.data);
      console.log(data);
      var newEvents = [];
      for(var i=0;data[i];i++){
        newEvents.push(data[i]);
      }
      this.setState({events:newEvents});
    })
  }

  subscribe = () => {
      console.log("Subscribing "+this.state.subMail);
  }

  unsubscribe = () => {
      console.log("Unsubscribing "+this.state.unsubMail);
  }

  handleChange = (e) => {
      this.setState({ [e.target.name] : e.target.value });
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          {this.state.events.map((event)=>{
            return(
          <Col sm="6" md="4" xl="3">
            <Card>
                <CardHeader>
                    <i className="fa fa-certificate"></i><strong>{event.event_text}</strong>
                </CardHeader>
                <CardBody>
                  {event.event_date.toLocaleString()}
                </CardBody>
            </Card>
          </Col>);
        }
          )}
        </Row>
      </div>
    );
  }
}

export default Events;
