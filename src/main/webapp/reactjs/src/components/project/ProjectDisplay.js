import {Component} from "react";

const defaultProps = {
    id: 0,
    project: null,
};

class ProjectDisplay extends Component {

    render() {
        return (
            <div>
                <h1>{this.props.project.name}</h1>
                ID: {this.props.project.id}<br />
                Tasks:
                <ul>
                    {this.props.project.tasks.map(task =>
                        <li key={task}>
                            {task}
                        </li>
                    )}
                </ul>
            </div>
        );
    }
}

ProjectDisplay.defaultProps = defaultProps;

export default ProjectDisplay;
