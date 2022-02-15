import {Component} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import ProjectDisplay from "./ProjectDisplay";
import {Alert} from "@mui/material";

class ProjectList extends Component {
    state = {
        list: [],
        page: 0,
        size: 0,
        totalElements: 0,
        totalPages: 0,
    }

    componentDidMount() {
        axios.get("http://localhost:8080/api/project")
            .then(value => {
                const projects = value.data;
                this.setState({
                    list: projects["content"],
                    page: projects["page"],
                    size: projects["size"],
                    totalElements: projects["totalElements"],
                    totalPages: projects["totalPages"],
                });
            })
    }

    render() {
        return (
            <div>
                <Alert severity="error">Test error alert</Alert>
                <ul>
                    { this.state.list.map(project =>
                        <li key={project.id}>
                            <ProjectDisplay project={project}/>
                            <Link to={`/projects/${project.id}`}>
                            </Link>
                        </li>
                    )}
                </ul>
            </div>
        );
    }
}

export default ProjectList;
