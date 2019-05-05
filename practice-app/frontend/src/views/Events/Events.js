import React, { Component, lazy, Suspense } from 'react';
import { Bar, Line } from 'react-chartjs-2';
import {
  Badge,
  Button,
  ButtonDropdown,
  ButtonGroup,
  ButtonToolbar,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  CardTitle,
  Col,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Form,
  Progress,
  Row,
  Input,
  InputGroup,
  InputGroupAddon,
  Table,
  ListGroup,
  ListGroupItem
} from 'reactstrap';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities'


class Events extends Component {
  constructor(props) {
    super(props);

    this.state = {
      events: [
        {
          event_text: "Event 1",
          event_date: new Date()
        },
        {
          event_text: "Event 2",
          event_date: new Date()
        },
        {
          event_text: "Event 3",
          event_date: new Date()
        },
        {
          event_text: "Event 4",
          event_date: new Date()
        },
        {
          event_text: "Event 5",
          event_date: new Date()
        },
        {
          event_text: "Event 6",
          event_date: new Date()
        },
      ]
    };
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
