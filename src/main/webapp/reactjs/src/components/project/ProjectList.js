import {Component} from "react";


class ProjectList extends Component {
    componentDidMount() {
        // TODO Retrieves projects
    }

    render() {
        // TODO
        return (
            <div>Test</div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        projects: [],
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        fetch
    }
};
