import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

export default function TextReader({ text }) {

    const modules = {
        toolbar: false,
    };
    return (
        <>
            <ReactQuill
                modules={modules}
                theme="snow"
                value={text}
                readOnly
            />
        </>
    );

}